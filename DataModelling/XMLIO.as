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
