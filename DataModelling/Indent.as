-- Routines for doing indentation
global indent
set indent to 1

global blanks
set blanks to "                                                         "

global indentAmount
set indentAmount to 2

on nSpaces(n)
	if n is less than 0 then return ""
	return characters 1 through n of blanks as string
end nSpaces

on curIndent()
	return nSpaces(indent * indentAmount) of me
end curIndent

on doIndent()
	local indentStr
	set indentStr to curIndent() of me
	set indent to indent + 1
	return indentStr
end doIndent

on unIndent()
	set indent to indent - 1
end unIndent
