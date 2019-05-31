
check:
	echo ${bintrayUser}
	echo ${bintrayKey}
	./gradlew -PdryRun=false -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} -PdryRun=true runtime:bintrayUpload
	./gradlew -PdryRun=false -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} -PdryRun=true processor:bintrayUpload

release: check
	./gradlew -PdryRun=false -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} runtime:bintrayUpload
	./gradlew -PdryRun=false -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} processor:bintrayUpload
