(* Copyright 2018 Joel Jones, joelkevinjones@gmail.com *)

-- TO DO;
-- + Disable the "continue" button until the script finishes. Is this possible?
-- + Modify specification of calls to print to include the calendar account, e.g. "commuting" => joelj@marvell.com:commuting"

global calsToPrint
set calsToPrint to {"Calendar", "teamjonesalameda@gmail.com", "US Holidays", "commuting"}

on clickButton(thePath, theValue)
	tell application "System Events"
		tell thePath
			click
			tell menu 1
				click menu item theValue
			end tell
		end tell
	end tell
end clickButton

on setCheckboxValue(thePath, desiredValue)
	tell application "System Events"
		tell thePath
			if value is not desiredValue then click
		end tell
	end tell
end setCheckboxValue

on setPopupValue(thePath, desiredValue)
	tell application "System Events"
		tell thePath
			if value is not desiredValue then
				click
				tell menu 1
					click menu item desiredValue
				end tell
			end if
		end tell
	end tell
end setPopupValue

on setTextFieldValue(thePath, desiredValue)
	tell application "System Events"
		tell thePath
			if value is not desiredValue then set value to desiredValue
		end tell
	end tell
end setTextFieldValue

on selectMenu(thePath)
	tell application "System Events"
		tell thePath
			click
		end tell
	end tell
end selectMenu

on clickIncrementor(thePath)
	tell application "System Events"
		tell thePath
			click
		end tell
	end tell
end clickIncrementor

on getTypeSignature(theRow, rowNum)
	local typeSignature
	set typeSignature to {}
	tell application "System Events"
		set rowContents to entire contents of theRow
		if (count of rowContents) is equal to 0 then
			--return {}
			return "empty"
		end if
		local checkboxCount
		local staticTextCount
		local groupCount
		local uiElementCount
		set checkboxCount to 0
		set staticTextCount to 0
		set groupCount to 0
		set uiElementCount to 0
		local elemsClassString
		set elemsClassString to ""
		repeat with theElem in items of rowContents
			--set elemsClassString to elemsClassString & " " & ((class of theElem) as text)
			if checkbox is equal to (class of theElem) then --�class sttx� 
				set checkboxCount to 1
			end if
			if static text is equal to (class of theElem) then
				set staticTextCount to 1
			end if
			if group is equal to (class of theElem) then
				set groupCount to 1
			end if
			if UI element is equal to (class of theElem) then
				set uiElementCount to 1
			end if
		end repeat
		--if rowNum is equal to 34 then
		--	display dialog ("elemsClassString: " & elemsClassString & " checkboxCount: " & --checkboxCount & " staticTextCount: " & staticTextCount & " groupCount: " & groupCount & --" uiElementCount: " & uiElementCount)
		--end if
		if groupCount is equal to 1 and uiElementCount is equal to 1 then
			if checkboxCount is equal to 1 and staticTextCount is equal to 1 then
				--return {groupCount:1, uiElementCount:1, checkboxCount:1, staticTextCount:1}
				return "grpUIcBsT"
			else
				display dialog "unrecognized rowContents 4"
			end if
		end if
		if checkboxCount is equal to 1 and staticTextCount is equal to 1 then
			--return {checkboxCount:1, staticTextCount:1}
			return "cBsT"
		else
			display dialog ("unrecognized rowContents 2 (" & rowNum & ") checkboxCount: " & checkboxCount & " staticTextCount: " & staticTextCount)
		end if
		--repeat with theElem in items of rowContents
		--	set typeSignature to addToUITypes(typeSignature, class of theElem as string) of me
		--end repeat
	end tell
	return typeSignature
end getTypeSignature

on selectCalsToPrint()
	tell application "System Events"
		set theCal to application process "Calendar"
		set printWin to window "Print" of theCal
		--set uiTypeSignatures to {}
		set rowNum to 0
		repeat with theRow in rows of outline 1 of scroll area 1 of printWin
			--set theRowType to rowType(getTypeSignature(theRow) of me) of me
			set rowNum to rowNum + 1
			set theRowType to getTypeSignature(theRow, rowNum) of me
			local theName
			local thePath
			set theName to ""
			if theRowType is "cBsT" then
				set theName to (name of item 1 of static text of theRow) as string
				set thePath to checkbox 1 of theRow
			end if
			if theRowType is "grpUIcBsT" then
				set theName to name of static text of theRow
				set thePath to checkbox 1 of group 1 of theRow
			end if
			if theName is not equal to "" then
				if theName is in calsToPrint then
					set desiredValue to 1
				else
					set desiredValue to 0
				end if
				setCheckboxValue(thePath, desiredValue) of me
			end if
		end repeat
	end tell
end selectCalsToPrint

on openPrint(theCal)
	tell application "System Events"
		selectMenu(menu item "Print�" of menu "File" of menu bar item "File" of menu bar 1 of theCal) of me
	end tell
end openPrint

on ensurePrintOpen()
	tell application "Calendar"
		local printNotOpen
		set printNotOpen to false
		activate
		try
			get window "Print"
		on error errstr number errnum
			if errnum is equal to -1728 then
				set printNotOpen to true
			else
				display dialog errstr
			end if
		end try
		if printNotOpen or not visible of window "Print" then
			tell application "System Events"
				set theCal to application process "Calendar"
				openPrint(theCal) of me
			end tell
			set visible of window "Print" to true
		end if
	end tell
end ensurePrintOpen

tell application "System Events"
	set theCal to application process "Calendar"
	ensurePrintOpen() of me
	set printWin to window "Print" of theCal
	setPopupValue(pop up button "View:" of printWin, "Week") of me
	setPopupValue(pop up button "Paper:" of printWin, "US Letter") of me
	setPopupValue(pop up button "Starts:" of printWin, "This week") of me
	setPopupValue(pop up button "Ends:" of printWin, "After") of me
	setTextFieldValue(text field 1 of printWin, "1") of me
	-- update "n weeks will be printed" string
	clickIncrementor(button 1 of incrementor 1 of printWin) of me
	clickIncrementor(button 2 of incrementor 1 of printWin) of me
	
	selectCalsToPrint() of me
	
	setCheckboxValue(checkbox "All-day events" of printWin, 1) of me
	setCheckboxValue(checkbox "Mini calendar" of printWin, 1) of me
	setCheckboxValue(checkbox "Calendar keys" of printWin, 1) of me
	setCheckboxValue(checkbox "Black and white" of printWin, 0) of me
	setPopupValue(pop up button 5 of printWin, "Medium") of me
end tell
