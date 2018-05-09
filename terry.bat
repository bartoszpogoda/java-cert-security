keytool -genkey -keystore terry.keystore -alias signTJars

jarsigner -keystore terry.keystore app.jar signTJars

keytool -export -keystore terry.keystore -alias signTJars -file Terry.cer