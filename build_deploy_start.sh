#!/bin/sh

# 0. Constants
PI_HOST="pi@pik.local"

# 1. Build using gradle
./gradlew linkReleaseExecutableNative

 # 2. Deploy using scp
scp build/bin/native/releaseExecutable/BlinktK.kexe $PI_HOST:~

# 3. Connect to pi
# and
# 4. Run
ssh $PI_HOST -t "~/BlinktK.kexe"

 # 5. Exit SSH session
exit