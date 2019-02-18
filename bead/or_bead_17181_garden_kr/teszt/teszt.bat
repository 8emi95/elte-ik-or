
@echo off

echo ===============================================
echo 1. feladat
echo ===============================================

javac -encoding utf8 -implicit:class -Xlint:unchecked -cp .;lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar gardentest\GardenRmiTest.java
IF ERRORLEVEL 1 GOTO end
java -cp .;lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar;lib/hsqldb.jar org.junit.runner.JUnitCore gardentest.GardenRmiTest
IF ERRORLEVEL 1 GOTO end

:end
