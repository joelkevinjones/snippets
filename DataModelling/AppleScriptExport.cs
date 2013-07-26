<?cs each: cls=DataModel.Classes ?>
on write<?cs var:cls.Name ?>(the<?cs var:cls.Name?>)
  advanceProgressBar(1) of me
  tell application "Palm Desktop"
    writeBeginEntry("<?cs var:cls.LCname ?>", the<?cs var:cls.Name ?>) of me<?cs each: prp=cls.Properties?>
    <?cs if:prp.AllowNull ?>if <?cs var:prp.ASaccessor ?> of the<?cs var:cls.Name ?> is not equal to missing value then 
      writeTagged("<?cs var:prp.Name ?>", <?cs var: prp.ASaccessor ?> of the<?cs var:cls.Name ?>) of me
    end if<?cs else ?>
    writeTagged("<?cs var:prp.Name ?>", <?cs var: prp.ASaccessor ?> of the<?cs var:cls.Name ?>) of me<?cs /if ?><?cs /each ?>
    writeEndTag("<?cs var:cls.LCname ?>") of me
  end tell
end write<?cs var:cls.Name ?>

on write<?cs var:cls.Name?>s()
  tell application "Palm Desktop"
    writeBeginTag("<?cs var:cls.LCname?>s") of me
    repeat with the<?cs var:cls.Name?> in <?cs var:cls.ASaccessor?>s
      write<?cs var:cls.Name?>(the<?cs var:cls.Name?>) of me
    end repeat
    writeEndTag("<?cs var:cls.LCname?>s") of me
  end tell
end write<?cs var:cls.Name?>s

on <?cs var:cls.LCname ?>AsRecord(the<?cs var:cls.Name ?>)
	local asRecord
	tell application "Palm Desktop"
		set the<?cs var:cls.Name ?> to properties of first <?cs var:cls.ASaccessor ?>
		<?cs set: numPrps = subcount(cls.Properties) ?><?cs set:cnt = #0 ?>
		set asRecord to {<?cs each:prp=cls.Properties ?><?cs var:prp.Name ?>:get <?cs var:prp.ASaccessor ?> of the<?cs var:cls.Name ?><?cs set: cnt = cnt + #1?><?cs if:cnt != numPrps ?>,<?cs /if ?><?cs /each ?>}
	end tell
	return asRecord
end <?cs var:cls.LCname ?>AsRecord
<?cs /each ?>

on main()
  try
    tell application "Palm Desktop"
      local itemCount
      set itemCount to getItemCount() of me
      startProgressBar(itemCount) of me
      openOutFile() of me
      writeXMLdecl() of me
      writeBeginTag("palmDesktop") of me
      <?cs each cls=DataModel.Classes?>
      setFooter("<?cs var:cls.Name?>s") of me
      write<?cs var:cls.Name?>s() of me<?cs /each ?>
      writeEndTag("palmDesktop") of me
      closeOutFile() of me
      if enableProgressBar then tell application "SKProgressBar" to quit
    end tell
  on error errText number errNum partial result partialResult
    closeOutFile() of me
    if enableProgressBar then tell application "SKProgressBar" to quit
    display dialog "main " & errText & errNum as string
    tell application "Palm Desktop"
      partialResult
    end tell
  end try
  tell application "SKProgressBar" to quit
end main

main() of me