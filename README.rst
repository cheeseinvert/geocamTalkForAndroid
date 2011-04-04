=========================================
GeoCam Talk for Android
=========================================

.. sectnum::

.. contents:: Contents

About
-----

The GeoCam Project helps people better understand and respond to disasters.
GeoCam consists of a GPS-enabled camera (or cell phone) and a web app for
sharing geotagged photos and other geospatial data.

GeoCam Talk is a communication application intended to aid in communication during a search mission. Members can send text and audio messages to any subset of their team members while on the field from their mobile devices. All messages are sent through a central server which catalogs them for later analysis. Just as in Memo, all messages are geotagged to provide instant awareness of team member location.

News
----

Visit http://sites.google.com/site/geocampracticum2011/ and http://disastercam.blogspot.com/ for updates.

Setup
-----
We used eclipse to do development of both the GeoCam Talk application and its associated Robolectric test project. As such, this README will provide directions on how to import and set up the projects in your eclipse instance.

Dependencies
~~~~~~~~~~~~
  * Eclipse IDE for Java Developers; Galileo or newer (http://www.eclipse.org/downloads/packages/)
  * The Android SDK with the 2.2 Google API (http://developer.android.com/sdk/installing.html)
  * The GeoCamMemoWeb project and its dependencies (https://github.com/cheeseinvert/geocamMemoWeb)

1. Once all dependencies have been installed, clone the GeoCamTalkForAndroid repository to the path of your choice ::

      git clone git@github.com:cheeseinvert/geocamTalkForAndroid.git

2. Launch your Eclipse instance and from the File menu, select `Import...`

   a. Select `General -> Existing Projects into Workspace`; Next >

   b. In the `Select root directory:` field, provide the location to which you cloned the repository
   
   c. In the `Projects` field, put a check box into both the `GeoCamTalkForAndroid` and `GeoCamTalkForAndroidTest` fields. You may need to click the Refresh button if the projects are not visible. Choose whether you'd like to copy the contents into your workspace and click `Finish`

Running
-------
From eclipse, select `Run > Run As... > Android Application` and select your target. For more information on running / emulating android applications from eclipse, see the official Android documentation: http://developer.android.com/guide/developing/building/building-eclipse.html

Testing
-------
The GeoCamTalkForAndroid project uses Robolectric in order to remove reliance on the emulator. Setting this up requires the following steps:

1. Right click the GeocamTalkForAndroidTest project from the package or file explorer and select `Run As > Run Configurations ...`

2. If a GeocamTalkForAndroidTest in the Junit section, select it. If no such entry exists, double click on the JUnit section to create one.

3. On the Test tab, set the `Test runner:` field to JUnit 4.

4. On the Arguments tab, choose the `Other` radio button in the `Working Directory` section. Click the `Workspace...` button.

   a. Click the GeocamTalkForAndroid project then click `OK`
   
5. Click `Run` to launch the test suite. If prompted to select a test runner, select the Eclipse JUnit test runner.

Future runs can be initiated by right clicking on the project and selecting `Run As > GeocamTalkForAndroidTest` among other methods. See eclipse documentation for additional shortcuts and methods of initiating junit tests.

| __BEGIN_LICENSE__
| Copyright (C) 2011 United States Government as represented by
| the Administrator of the National Aeronautics and Space Administration.
| All Rights Reserved.
| __END_LICENSE__
