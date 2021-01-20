FROM gitpod/workspace-full

RUN sudo wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN sudo apt-get -q update
RUN sudo apt -y install ./google-chrome-stable_current_amd64.deb
RUN sudo apt-get install -yq libnss3 libx11-xcb-dev && \
    sudo rm -rf /var/lib/apt/lists/*

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
    && sdk update \
    && sdk install java 11.0.9-amzn \
    && sdk default java 11.0.9-amzn"
