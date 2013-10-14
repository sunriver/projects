echo "step1: clean"
ant clean
#
echo "step2: release"
ant release
#
echo "copy apk to output directory"
cp bin/*release.apk ~