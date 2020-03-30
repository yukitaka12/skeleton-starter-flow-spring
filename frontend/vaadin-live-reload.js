import {LitElement, html, css} from 'lit-element';

class VaadinLiveReload extends LitElement {

    static get styles() {
        return css`
      .vaadin-live-reload > div {
         box-shadow: 2px 2px 2px grey;
         background-color: #333; 
         color: #DDD;
      }
      
      .gizmo { 
          position: fixed;
          right: 15px;
          top: 10px;
          z-index: 20000;
      }
      
      .vaadin-logo {
          font-size: 20px;
          font-weight: bold;
          text-align: center;
          vertical-align: middle; 
          line-height: 50px; 
          transform: rotate(90deg);
          border-radius: 50%;
          width: 50px;
          height: 50px;
      }
      
      .notification {
        padding: 4px 20px 4px 10px;
        border-radius: 8px;
      }
      
      .status-blip {
          position: fixed;
          right: 15px;
          top: 10px;
          background-color: green;
          border: 3px solid black;
          border-radius: 50%;
          width: 10px;
          height: 10px;
          z-index: 20001;
      }
      
      .window {
          position: fixed;
          right: 10px;
          top: 65px;
          border-radius: 8px;
          border: #AAA;
          width: 30%;
          font-size: 14px;
      }
      
      .window-header {
          background-color: #222;
          border-radius: 8px 8px 0px 0px;
          border-color: #AAA;
          padding: 4px;
          text-align: right;
      }
      
      .message-tray > div {
          padding: 6px;
          border-top: 1px solid #444;
      }
      
      .message-tray > div:before {
          content: "ⓘ";
          margin-right: 4px;
      }
    `;
    }

    static get properties() {
        return {
            expanded: {type: Boolean},
            messages: {type: Array},
            status: {type: String},
            notification: {type: String}
        }
    }

    static get ACTIVE() {
        return 'active';
    }

    static get INACTIVE() {
        return 'inactive';
    }

    static get UNAVAILABLE() {
        return 'unavailable';
    }

    static get ERROR() {
        return 'error';
    }

    static get ENABLED_KEY_IN_LOCAL_STORAGE() {
        return "vaadin.live-reload.enabled";
    }

    static get ACTIVE_KEY_IN_SESSION_STORAGE() {
        return "vaadin.live-reload.active";
    }

    static get TRIGGERED_KEY_IN_SESSION_STORAGE() {
        return "vaadin.live-reload.triggered";
    }

    static get SPRING_DEV_TOOLS_PORT() {
        return 35729;
    }

    static get isEnabled() {
        let enabled = window.localStorage.getItem(VaadinLiveReload.ENABLED_KEY_IN_LOCAL_STORAGE);
        return enabled === null || !(enabled === 'false');
    }

    static get isActive() {
        let active = window.sessionStorage.getItem(VaadinLiveReload.ACTIVE_KEY_IN_SESSION_STORAGE);
        return active === null || active !== 'false';
    }

    constructor() {
        super();
        this.messages = [];
        this.expanded = false;
        this.status = VaadinLiveReload.UNAVAILABLE;
        this.notification = null;
        this.connection = null;
    }

    openWebSocketConnection() {
        if (this.connection !== null) {
            this.connection.close();
            this.connection = null;
        }
        let self = this;
        let hostname = window.location.hostname;
        // try Spring Boot devtools first
        self.connection = new WebSocket(
            "ws://" + hostname + ":" + VaadinLiveReload.SPRING_DEV_TOOLS_PORT);
        self.connection.onmessage = msg => self.handleMessage(msg);
        self.connection.onerror = e => {
            // TODO: Use the passed service URL
            let url = window.location.toString().replace("http://", "ws://")
                + '?refresh_connection';
            self.connection = new WebSocket(url);
            self.connection.onmessage = msg => self.handleMessage(msg);
            self.connection.onerror = e => self.handleError(e);
        };
    }

    handleMessage(msg) {
        let json = JSON.parse(msg.data);
        let command = json['command'];
        switch (command) {
            case 'hello':
                if (VaadinLiveReload.isActive) {
                    this.status = VaadinLiveReload.ACTIVE;
                } else {
                    this.status = VaadinLiveReload.INACTIVE;
                }
                this.showMessage('Live reload available');
                this.connection.onerror = e => self.handleError(e);
                break;

            case 'reload':
                if (this.status === VaadinLiveReload.ACTIVE) {
                    this.showNotification('Reloading...');
                    let now = new Date();
                    let reloaded =  ("0" + now.getHours()).slice(-2) + ":"
                        + ("0" + now.getMinutes()).slice(-2) + ":"
                        + ("0" + now.getSeconds()).slice(-2);
                    window.sessionStorage.setItem(VaadinLiveReload.TRIGGERED_KEY_IN_SESSION_STORAGE, reloaded);
                    window.location.reload();
                }
                break;

            default:
                console.warn("unknown command:", command);
        }
    }

    handleError(msg) {
        console.error(msg);
        this.status = VaadinLiveReload.ERROR;
    }

    connectedCallback() {
        super.connectedCallback();
        this.disableEventListener = e => this.demoteNotification();
        document.body.addEventListener('focus', this.disableEventListener);
        document.body.addEventListener('click', this.disableEventListener);
        if (!VaadinLiveReload.isEnabled) {
            // TODO: should not be created at all if disabled in local storage
            this.disableLiveReload();
        } else {
            this.openWebSocketConnection();
        }

        let lastReload = window.sessionStorage.getItem(VaadinLiveReload.TRIGGERED_KEY_IN_SESSION_STORAGE);
        if (lastReload) {
            this.showNotification("Last automatic reload on " + lastReload);
            window.sessionStorage.removeItem(VaadinLiveReload.TRIGGERED_KEY_IN_SESSION_STORAGE);
        }
    }

    disconnectedCallback() {
        document.body.removeEventListener('focus', this.disableEventListener);
        document.body.removeEventListener('click', this.disableEventListener);
        super.disconnectedCallback();
    }

    disableLiveReload() {
        window.localStorage.setItem(VaadinLiveReload.ENABLED_KEY_IN_LOCAL_STORAGE, 'false');
        if (!!this.id) {
            let element = document.getElementById(this.id);
            element.parentNode.removeChild(element);
        }
    }

    toggleExpanded() {
        this.expanded = !this.expanded;
    }

    showNotification(msg) {
        this.notification = msg;
    }

    showMessage(msg) {
        this.messages.push(msg);
    }

    demoteNotification() {
        if (this.notification) {
            this.showMessage(this.notification);
        }
        this.showNotification(null);
    }

    setActive(yes) {
        if (yes) {
            window.sessionStorage.setItem(VaadinLiveReload.ACTIVE_KEY_IN_SESSION_STORAGE, 'true');
            this.status = VaadinLiveReload.ACTIVE;
        } else {
            window.sessionStorage.setItem(VaadinLiveReload.ACTIVE_KEY_IN_SESSION_STORAGE, 'false');
            this.status = VaadinLiveReload.INACTIVE;
        }
    }

    getStatusColor() {
        if (this.status === VaadinLiveReload.ACTIVE) {
            return 'green';
        } else if (this.status === VaadinLiveReload.INACTIVE) {
            return 'grey';
        } else if (this.status === VaadinLiveReload.UNAVAILABLE) {
            return 'yellow';
        } else if (this.status === VaadinLiveReload.ERROR) {
            return 'red';
        } else {
            return 'none';
        }
    }

    render() {
        return html`
            <div class="vaadin-live-reload">
                ${this.notification !== null
            ? html`<div class="gizmo notification" @click=${e => this.toggleExpanded()}>${this.notification}</div>`
            : html`<div class="gizmo vaadin-logo" @click=${e => this.toggleExpanded()}>}&gt;</div>`}

                <span class="status-blip" style="background-color: ${this.getStatusColor()}"></span>
                <div class="window" style="visibility: ${this.expanded ? 'visible' : 'hidden'}">
                    <div class="window-header">
                        <input type="button" value="Disable" @click=${e => this.disableLiveReload()}/>
                        <input type="checkbox" ?checked="${this.status === VaadinLiveReload.ACTIVE}" 
                        @change=${e => this.setActive(e.target.checked)}>Live-reload</input>
                    </div>
                    <div class="message-tray">
                         ${this.messages.map(i => html`<div>${i}</div>`)}
                    </div>
                </div>
            </div>`;
    }
}

customElements.define('vaadin-live-reload', VaadinLiveReload);
