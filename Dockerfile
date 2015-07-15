FROM whiteworld/android

MAINTAINER White World "ljq258@gmail.com"

VOLUME  /android

## for debug
# COPY . /android

# Build the Android application within the docker container.
RUN gradle assembleDebug
