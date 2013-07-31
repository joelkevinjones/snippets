-- Simple routines for handling a progress bar
global enableProgressBar
set enableProgressBar to false

on startProgressBar(theTitle, theHeader, itemCount)
	if enableProgressBar then
		tell application "SKProgressBar"
			activate
			set floating to false
			set position to {100, 100}
			set width to 600
			set title to "Palm Desktop XML Export"
			set header to "Exporting to XML"
			tell progress bar
				set minimum value to 0
				set maximum value to itemCount
				set current value to 0
				set indeterminate to false
			end tell
			set show window to true
		end tell
	end if
end startProgressBar

on setFooter(msg)
	if enableProgressBar then
		tell application "SKProgressBar" to set footer to msg
	end if
end setFooter

on advanceProgressBar(amt)
	if enableProgressBar then
		tell application "SKProgressBar"
			tell progress bar
				start animation
				increment by amt
				stop animation
			end tell
		end tell
	end if
end advanceProgressBar
