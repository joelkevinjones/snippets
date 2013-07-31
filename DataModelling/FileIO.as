on openOutFile(defFileName)
	local outFile
	local outFD
	set outFile to choose file name default name defFileName default location (path to home folder) with prompt "Choose a name and a location to export to."
	set outFD to open for access outFile with write permission
	set eof outFD to 0
	return outFD
end openOutFile

on closeOutFile(outFD)
	if outFD is not equal to -1 then
		close access outFD
	end if
end closeOutFile

on writeLine(outFD, str)
	local outBuf
	set outBuf to str & (character id 10) -- new line
	write outBuf to outFD
end writeLine
