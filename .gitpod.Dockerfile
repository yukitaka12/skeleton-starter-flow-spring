FROM gitpod/workspace-full

RUN sudo wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN sudo apt-get update
RUN sudo apt -y install ./google-chrome-stable_current_amd64.deb
