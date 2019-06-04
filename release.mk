
check:
	echo ${bintrayUser}
	echo ${bintrayKey}
	./gradlew -PdryRun=true -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} runtime:bintrayUpload --info
	./gradlew -PdryRun=true -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} processor:bintrayUpload --info

release: check
	./gradlew -PdryRun=false -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} runtime:bintrayUpload
	./gradlew -PdryRun=false -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} processor:bintrayUpload
