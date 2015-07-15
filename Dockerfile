FROM whiteworld/android

MAINTAINER White World "ljq258@gmail.com"

COPY . /android

# Build the Android application within the docker container.
RUN gradle assembleDebug
