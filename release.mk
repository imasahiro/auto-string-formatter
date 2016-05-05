
check:
	echo ${bintrayUser}
	echo ${bintrayKey}

release:
	./gradlew :release

upload: check
	./gradlew -PdryRun=false -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} :runtime:bintrayUpload --info
	./gradlew -PdryRun=false -PbintrayUser=${bintrayUser} -PbintrayKey=${bintrayKey} :processor:bintrayUpload --info
