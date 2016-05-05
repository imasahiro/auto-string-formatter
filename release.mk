
check:
	echo ${bintrayUser}
	echo ${bintrayKey}
	./gradlew verifyRelease

release: check
	./gradlew -PdryRun=false -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} runtime:bintrayUpload
	./gradlew -PdryRun=false -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} processor:bintrayUpload
