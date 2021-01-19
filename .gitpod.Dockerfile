FROM gitpod/workspace-full

RUN sudo apt-get -q update && \
    sudo apt-get install -yq chromium-browser && \
    sudo rm -rf /var/lib/apt/lists/*

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
    && sdk update \
    && sdk install java 11.0.9-amzn \
    && sdk default java 11.0.9-amzn"
