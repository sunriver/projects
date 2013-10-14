echo "step1: clean"
ant clean
echo "step2: release"
ant release
echo "copy apk to release directory"
cp bin/*release.apk ../../build/release/listen/