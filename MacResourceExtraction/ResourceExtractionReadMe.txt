Palm Desktop has the following resource types, obrtained by running the
following command after running extract_rsrc.pl:
for f in tmp28930/*.rsrc; do   basename "${f}" | awk '{print substr($0,1,4)}'; done | sort | uniq -c | sort -n
BBLA BNDL CNTL CURS FMNU FREF ICN# ICON NAVT PICT POrg QUIL SICN SIZE STR#
TREC WDPS acur carb cfrg cicn icl4 icl8 icm# icm4 icm8 icns ics# ics4 ics8
open scpt snd  vers

After running rezilla on Localized:
ls Resources\ from \ \"Localized.rsrc\"/ | sed -e s@\'@@g | sort
AEIP ALRT BKTM BLAY CNTL CNV3 DITL DLOG FLD JAPL JBFL JCFL JEFL JFFL JIFL JLFL
JNFL MENU NEWF NRCT OITL PICT POrg PPIN PREF STR STR# SpSc TEXT TLAY TMPL WIND
WTBB WTCA WTCD WTCT WTDH WTLV WTMV WTND WTPB WTPP WTTB XPDO actb aete cctb dftb
dlgx hfdr hmnu kind mTbl mctb ppat xmnu

ls Resources\ from\ \"Palm\ Desktop\ Fonts\"/ | sed -e s@\'@@g | sort
FOND fwst kvst kwst movp sfnt vers

Bibliography
MTBE Macintosh Toolbox Essentials
TEXT Inside Macintosh: Text

Resource Purpose and Format
'actb' Alert color table for ALRT with same ID. same as wctb.
'acur' animated cursor, composed of a series of 'CURS" resources. Number of
'AEIP' PD specific, 00128 "Attach Existing Items", 91 bytes
'aete' Apple event describes which of the standard suites listed in the 'aeut'
  resource it supports and providing other application-specific information.
  "Palm Desktop Terminology". Contains major, minor version, language code,
  array of Suites. Each suite contains name, description, Id, level, version,
  array of events, array of classes, array of comparison ops, array of
  enumerations. Each event contains name, description, class, ID, reply type,
  reply description, reply required, reply single/list, reply enumerated,
  direct param type, direct param required/optional, array of other params.
  Each class contains name, ID, description, array of properties, array of
  elements. etc.
'ALRT' Specifies alert sounds, display rectangle, and an item list for an
  alert box ('DITL').
'BBLA' PD specific, "Button bar layout" #128, 82 bytes 
'BKTM' PD specific, paper planner information, 16 bytes
'BLAY' PD specific, paper planner information, > 2xx bytes
'BNDL' associates application and its documents with their icons. Contains the
  application's signature, resource ID of signature resource (always 0),
  assignment of local IDs to the resource IDs of all icon list resources
'carb' PD specific? 4 bytes, 0
'cctb' Control color table, contains part id rgb pairs.
'cfrg' Code fragment reserved (10), version (2), reserved (18), member count
  (2), {CFragResourceMember}*. A CFragResourceMember identifies the
  architecture updateLevel, version information, stack size, fragment type,
  where code is located, offset and length, size, name, etc.
'cicn' color icons within application (not Finder). Icon's pixel map (50),
  Mask bitmap (14), Icon's bitmap(14), Icon's data (4), Mask bitmap image data
  (variable), Icon's bitmap image data (variable), Icon's color table
  (variable), Pixel map image data (variable)
'CNTL' describes control; referenced by DITL. Contains rectangle in window
  local coordinates that encloses control (4), initial setting for control (2),
  visible? (1), fill 0 (1), maximum setting (2), minimum setting (2), control
  definition ID (2), reference value (2), title string (varies)
'CNV3' PD specific, localization? "U.S.", "France", varying size
'CURS' 16 x 16 black-and-white cursor Bits16 = ARRAY[0..15] of Integer, data
  Bits16, mask Bits16, hotSpot Point
'dftb' Dialog Control Font Table, q.v. Dialogs.[rh]
'DITL' List of items to be displayed in a dialog box. The items may be
  buttons, check boxes, static text, editable text, a control, icon, picture,
  user defined, help, etc.
'dlgx' Extended Dialog template: case versionZero 0 (2) long int q.v.
  Dialogs.h, in Localized. 00099 (all): 0xb => use theme bkgd, use control
  hier, use theme controls
'DLOG' Specifies a dialog box. Has rectangle, window definition, flags, DITL
  ID, title, position
'FLD ' PD specific, database field descriptions?, 88 bytes
'FMNU' PD specific? "Font Menu Info", 000b 0100 pstr "FM Pro Fonts"
'FOND' Font family see TEXT p. 4.91
'FREF' link icons with the file types they represent. File type four-character
  code that identifies file type (4), Local ID maps file type in this resource
  to an icon list resource with the ssame local ID in the fundle resource (4),
  empty string (1)
'hfdr' Help balloon. Contains help manager version, options, Balloon
  definition function, variantion code, icon components. An icon component has
  size, type, and type specific data.
'hmnu' help menu. Contains verion, options, balloon definition function,
  variation code, item count, missing-items component, menu-title component,
  first through last menu-item components. Menu item components have size,
  type, and four text strings
'icl4' Large (32x32) color icon with 4 bits/pixel, using the standard 16
  color palette
'icl8' Large (32x32) color icon with 8 bits/pixel, using the standard 256
  color palette.
'icm#' Icon list for mini icons (12x16) using 16 and 256 color palettes (or 1
  bit? see Palm Desktop and Resources/English.lproj/Decors/Red Chile Peppers)
'icm4' mini 4-bit color icon resources (12x16) (right side blank)
'icm8' mini 8-bit color icon resources (12x16) (right side blank)
'icns' non-classic icon list? (PD) q.v.
  https://en.wikipedia.org/wiki/Apple_Icon_Image_format starts with 'icns'
  followed by 4 byte length, then list of icons. Each icon has 4 byte icon
  type, 4 byte length, followed by length - 8 bytes of data. The type codes
  appear to match the resources type codes, i.e. ICON, ICN#, icm#, icm4, icm8,
  ics#, ics4, ics8, plus others. It also has 'TOC ' table of contents listing
  all image types in file and their sizes, 'icnV' 4 byte BE float of Icon
  Composer app that created, 'name' ?, 'info' info binary plist
'ICN#" icon list one black-and-white icon, 32x32. 32x32 black-and-white icon,
  32x32 icon mask
'ics#' small (16x16) B&W icon, with mask
'ics4' small (16x16) 4-bit color icon with 16 color palette
'ics8' small (16x16) 8-bit color icon with 256 color palette
'ICON' 32x32 bit B&W pixel (128)
'JAPL' PD specific, Adressing printing layout, 7-24 bytes
'JBFL' PD specific, Book Form Layout, 80-95 bytes
'JCFL' PD specific. Calendar Form Layout
'JEFL' PD specific. Envelope Form Layout
'JFFL' PD specific. Fax Form Layout
'JIFL' PD specific. List form layout
'JLFL' PD specific. Label Form layout
'JNFL' PD specific. Memo (Note) Form layout
'kind' Overrides Finder's normal algorithm for generating kind strings.
  Contains application signature, region code, kind array count, {file type (4),
  kind string (null terminated)}* signature: 'POrg', region: 0, filler: 0,
  count: 3, 1: apnm -> Palm Desktop, JJJS -> Palm Desktop Document, CIFT -> Palm  Desktop Special Import Document
'mctb' menu color information table. Contains number of entries, entries. Each
  entry contains ID, Item number, four RGBs interpreted based on whether menu,
  menu title, etc.
'MENU' Menus. Contains: Menu ID (2), plchldr width (2), plchldr height (2),
  resource ID of menu definition proc (2), plchldr, initial enabled state of
  the menu and menu items (4), title pstr, array of menu items (variable).
  Menu items contain: item text pstr, icon number/script/0 (1), keyboard
  equiv/0x1b,0x1c,0x1d,0x1e,p (1), marking character or menu id of submenu (1),
  style (1) MTBE p. 3-152 (Localized). Keyboard equivalents (from
  https://en.wikipedia.org/wiki/Mac_OS_Roman) 0x11 Command key U+2313 PLACE
  OF INTEREST, 0x12 shift U+21E7 UPWARDS RIGHT ARROW, 0x13 option U+2325
  OPTION KEY, 0x14 control U+2303 UP ARROWHEAD (instead of caret).
'mTbl' PD specific. Map from 2 byte sequence 0-23, 29-36, 4000, 4001, 4a00 
  -> 0
'NAVT' PD specific, describes file types, begins POrg, 00200 "Open File Types"
  two entries, JJJS and CIFT, 00201 "Merge File Types", one entry JJJS
'NEWF' PD specific, template for creating PD data files? "New File Copy", "Raw
  New File", strings for field names, default field values, other text
'NRCT' PD specific, like 'nrct'? list of rectangles? "Memo Window Scalars"
'OITL' PD specific, "International configuration", 58 bytes 
'open' 00128 "Types that can be dragged onto app" application signature,
  count, entries: POorg, 7, JJJS CIFT vCrd vCal VCRD VCAL TEXT (JJJS is Claris
  Organizer?)
'PICT' Quickdraw picture. Size (only version 1) (2), Picture frame/bounding
  rectangle (8), Opcode array (variable). See Appendix A of "Imaging with
  QuickDraw" for picture opcodes.
'POrg' PD specific, "Owner Resource", single byte, 0, cross reference with
  'NAVT'?
'ppat' Pixel pattern. See Quickdraw.r in MacOSX10.6.sdk and
  classic-mac-utils/ppat2png.pl.
'PPIN' PD specific, "Paper Planner Interview Info", 410 bytes
'PREF' (maybe? ResEdit: store preference resource to ResEdit Preferences file.
  ID number is ten times the ID number of your editor. RE p. 125) (localized,
  128), 428 bytes, format? contains Hayes modem commands? ATE1Q0 and ATH0,
  AT - attention E1 - echo commands Q0 - displays result codes H0 - on hook
'QUIL' PD specific, 00220 "Font Numbers", sequence of 2 bytes for fonts: 2b5f
  -> 11,103b10, index into Resources/Fonts/Palm Desktop Fonts resource fork,
  'FOND' resource, and others, 'sfnt' TrueType outline font
'scpt' Compiled script file, normal osadecompile works, needs 'aete' somehow
'SICN' lists of 12x16 B&W icon, stored as 16x16, by convention only two, 2nd
  being mask. Used in menus, but not finder. In PD, left triangle and document
  cursors (2), Next Frame to show (2), {Resource ID for frame(2), pad(2)}*
'SIZE' Size of application's partition and other information. Contains a 16
  bit flags field, followed by two 32-bit size fields. The size fields
  specify minimum partition size and preferred partition size. ID is -1.
  Flags: accept suspend&resume events, canBackground, doesActivateonFGSwitch,
  has user interface, get front clicks on resume, ignore app died events,
  is 32 bit compatible, is high-level event aware, remote high-level events,
  stationary, use Text Edit Services. (some bits are reserved.) MTBE p. 65
'snd ' Sound, has two formats, format 1 and format 2. PD has mix of both, even
  though format 2 is obsolete. Format 2 is used for phone dialing sounds.
  Others are used for "Drag initiate", "Enter Object", "Dropped",
  "Attach-Stapler"
'SpSc' PD specific. (Localized, contains 'KENW')
'STR ' a single string, no length, no trailing null terminator
'STR#' String list. Two byte count followed by pstrs. (translate to .strings?)
'TEXT' CR delimited text, can have 'styl' applied to it.
'TLAY' PD specific. Address formats for different countries, plus "Full Name",
  "Name Expando" 37-51 bytes
'TMPL' Template for editing resources, used by ResEdit and extended by
  Resourcerer. Contains array of pstr and 4 byte code pairs. See
  ~/Documents/Inside%20Macintosh/Resorcerer%20Template%20Information.webarchive
'TREC' PD specific. Printer information? "Landscape - LaserWriter", "Portrait
  - DeskWriter" 120 bytes.
'vers' Version information about file(s). Major revision level (BCD,1), Minor
  revision level (BCD,1), Development stage (enum, 1), prerelease revision
  level (BCD?, 1), region code (script system, 2), Version number pstr, Version
  message pstr
'wctb' Window color table. Contains: pad (6), number entries - 1 (2), {part
  identifier (2), RGB (6)}*. Part identifier enum: content region background,
  frame color, window title and button text, lightest stripes in title bar
  and lightest dimmed text, darkest stripes in color bar and darkest dimmed
  text, lightest parts of title bar background, darkest parts of title bar
  background, lighest element of dialog box frame, darkest element of dialog
  box frame, lightest window tinging, darkest window tinging. 
  MTBE pp.  4-128--4-129.
'WDPS' PD specific, 00128 "Word Processors Script Info", entry count (2),
  entries: 4 char type x 4 char application , 10 bytes, 1st: BOBO CWWP, 2nd:
  MWPR MWPd trailing two bytes 1c2 -> 450b10, 1c3 -> 451b10
'WIND' Characteristics of windows. Contains: initial rectangle (8), window
  definition ID (2), visibility status (2), presence of close box (2),
  reference constant (4), window title pst, positioning specification (2).
  MTBE pp 4-124--4-127.
'WTBB' PD specific. Described by TMPL as "Button Bar", but TMPL doesn't match
  contents. See classic-mac-utils/dumpTMPL.pl to dump TMPL
'WTCA' PD specific. Described by TMPL as "Calendar"
'WTCD' PD specific. Described by TMPL as "Contact Detail"
'WTCT' PD specific. Described by TMPL as "Categories"
'WTDH' PD specific. Described by TMPL as "Drag Handle"
'WTLV' PD specific. Described by TMPL as "List Views"
'WTMV' PD specific. Described by TMPL as "Monthly Calendar"
'WTND' PD specific. Described by TMPL as "Notes Detail"
'WTPB' PD specific. Described by TMPL as "Push Button"
'WTPP' PD specific. Described by TMPL as "Popup"
'WTTB' PD specific. Described by TMPL as "Tab"
'xmnu' PD specific. "Edit Menu" "View" "Date Book submenu" 34-226 bytes, "Edit
  Menu" contains undo, cut, copy, past, sall
'XPDO' PD specific. Expando "Name Expando" "Phone Expando" "Primary Address
  Expando" etc. 8 bytes probably coordinates for pop-up editing
