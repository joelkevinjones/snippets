Claris Organizer in Java (COinJ)
===============================

My favorite Personal Information Manager (PIM) of all time is Claris 
Organizer (CO).
CO was purchased (or licensed) by Palm to become Palm Desktop (PD).
I exclusively used these on the Macintosh, until Mac OSX dropped support for
Mac Classic PowerPC programs. I have been on an extremely slow motion,
extremely intermittent quest ever since to recreate CO/PD in a platform
independent, but platform conforming way.

The recreation task has taken several paths. 

The first task is to extract information from a running Palm Desktop.
There are two attempts.
First is in AppleScript/PalmDesktop/ExportToXML.applescript.
This is a hand-written extractor.
The second is in DataModelling.
The idea here is to generate the extraction software using a model of the
data contained in PD.
It uses clear silver.

The second task is to extract the resources of the PD application, especially
decor information.
This is contained in MacResourceExtraction.
There are several tools have been used to do this, but the current intent
is to continue to extend the class-mac-utils.

The third task is to emulate the inter-linking abilities of PD by using
Mac OS application URLs and AppleScript.
This an interim solution to get some of the capablities until a complete
replacement application is created.
This is contained in the separate repo COinJ_AppleScript.
There are scripts to put into the pasteboard/copy buffer URLs for Calendar,
Contacts, Evernote, Finder, Safari, and Mail.

The fourth task is the application itself.
There is a lot of Java code that has examples of the various GUI components
with dummy event/action handlers.


