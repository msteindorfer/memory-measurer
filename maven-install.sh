mvn install:install-file \
  -DgroupId=com.google \
  -DartifactId=memory-measurer \
  -Dpackaging=jar \
  -Dversion=1.0-SNAPSHOT \
  -Dfile=dist/object-explorer.jar \
  -DgeneratePom=true