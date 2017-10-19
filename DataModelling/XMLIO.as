on writeXMLdecl(outFD)
	writeLine(outFD, "<?xml version=\"1.0\" encoding=\"macintosh\" ?>") of me
end writeXMLdecl

on writeBeginTag(outFD, tagName)
	local tag
	set tag to doIndent() of me & "<" & tagName & ">"
	--display dialog "writeBeginTag" & tag
	writeLine(outFD, tag as string) of me
	return tag
end writeBeginTag

on writeEndTag(outFD, tagName)
	local tag
	set tag to curIndent() of me & "</" & tagName & ">"
	unIndent()
	--display dialog tag
	writeLine(outFD, tag as string) of me
	return tag
end writeEndTag
property searchList : {"<", ">", "&", "\"", "'"}
property replaceList : {"&lt;", "&gt;", "&amp;", "&quot;", "&#039;"}

to toXML(t)
	local escStr
	local escItem
	set escStr to ""
	set t to t's text items
	considering case
		repeat with i from 1 to count t
			set escItem to item i of t
			repeat with n from 1 to count searchList
				if escItem is equal to item n of searchList then
					set escItem to item n of replaceList
				end if
			end repeat
			set escStr to escStr & escItem
		end repeat
	end considering
	escStr
end toXML

on writeTagged(outFD, tagName, tagData)
	local taggedData
	if tagData = missing value then return ""
	if tagData is equal to "" then return ""
	--display dialog "writeTagged, tagData before toXML " & tagData
	set tagData to tagData as string
	set tagData to toXML(tagData) of me
	set taggedData to curIndent() of me & "<" & tagName & ">" & tagData & "</" & tagName & ">"
	writeLine(outFD, taggedData) of me
	return taggedData
end writeTagged

-------------------------
on getIDForEntry(theEntry)
	local idString
	set idString to id of theEntry
	set idString to (class of theEntry as string) & idString
	-- replace spaces with ""
	return idString
end getIDForEntry

on writeLinkTo(tagName, theEntry)
	local tag
	local idString
	if theEntry = missing value then return
	tell application "Palm Desktop"
		set idString to getIDForEntry(theEntry) of me
		display dialog "idString: " & idString
		set tag to curIndent() of me & "<" & tagName & " xref=\"ID" & (get id of theEntry as string) & "\"/>"
		--display dialog "writeLinkTo " & tag
		writeLine(tag as string) of me
	end tell
end writeLinkTo

on writeCategoryLinkTo(tagName, theCategory)
	local tag
	local idString
	if theCategory = missing value then return
	tell application "Palm Desktop"
		set idString to getIDForEntry(theEntry) of me
		set tag to curIndent() of me & "<" & tagName & " xref=\"" & idString & "\"/>"
		--display dialog "writeCategoryLinkTo " & tag
		writeLine(tag as string) of me
	end tell
end writeCategoryLinkTo

on writeBeginEntry(tagName, theEntry)
	local tag
	local idString
	tell application "Palm Desktop"
		set idString to getIDForEntry(theEntry) of me
		set tag to doIndent() of me & "<" & tagName & " xml:id=\"" & idString & "\">"
		writeLine(tag) of me
		--display dialog "writeBeginEntry " & tag
		writePrimaryCategory(theEntry) of me
		writeSecondaryCategory(theEntry) of me
		writeTagged("private", get private of theEntry) of me
		writeAttachments(get attachments of theEntry) of me
	end tell
end writeBeginEntry
