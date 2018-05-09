keytool -genkey -keystore chris.keystore -alias signJars

jarsigner -keystore chris.keystore lib.jar signJars

keytool -export -keystore chris.keystore -alias signJars -file Chris.cer
