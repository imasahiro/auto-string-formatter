
check:
	./gradlew verifyRelease

release: check
	./gradlew release
	./gradlew -PdryRun=false -PbintrayUser=$bintrayUser -PbintrayKey=$bintrayKey runtime:bintrayUpload
	./gradlew -PdryRun=false -PbintrayUser=$bintrayUser -PbintrayKey=$bintrayKey processor:bintrayUpload
	./gradlew publish
