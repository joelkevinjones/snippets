<?cs include:"ProgressBar.as" ?>
<?cs include:"XMLIO.as" ?>
<?cs include:"FileIO.as" ?>
<?cs include:"Indent.as" ?>
global outFD
set outFD to -1

<?cs # set some parameters for testing 
?><?cs set:idxLimit = 5 ?>
<?cs # count of  var:cls.LCname s 
?>
<?cs # Macros for dependent types 
?><?cs 
def:genDepTypeWriter(depType) ?>
on write<?cs var:depType.Name ?>(outFD, the<?cs var:depType.Name?>)
  tell application "Palm Desktop"
  end tell
end write<?cs var:depType.Name?>
<?cs /def 
?><?cs 
# Macros for independent types
?><?cs 
def:genIndyTypeWriter(cls) ?>
on write<?cs var:cls.Name ?>(outFD, the<?cs var:cls.Name?>)
  advanceProgressBar(1) of me
  tell application "Palm Desktop"
    writeBeginEntry(outFD, "<?cs var:cls.LCname ?>", the<?cs var:cls.Name ?>) of me<?cs each: prp=cls.Properties?>
    <?cs if:prp.AllowNull ?>if <?cs var:prp.ASaccessor ?> of the<?cs var:cls.Name ?> is not equal to missing value then 
      writeTagged(outFD, "<?cs var:prp.Name ?>", <?cs var: prp.ASaccessor ?> of the<?cs var:cls.Name ?>) of me
    end if<?cs else ?>
    writeTagged(outFD, "<?cs var:prp.Name ?>", <?cs var: prp.ASaccessor ?> of the<?cs var:cls.Name ?>) of me<?cs /if ?><?cs /each ?>
    writeEndTag(outFD, "<?cs var:cls.LCname ?>") of me
  end tell
end write<?cs var:cls.Name ?>

on <?cs var:cls.LCname ?>AsRecord(the<?cs var:cls.Name ?>)
	local asRecord
	tell application "Palm Desktop"
		set the<?cs var:cls.Name ?> to properties of first <?cs var:cls.ASaccessor ?>
		<?cs set: numPrps = subcount(cls.Properties) ?><?cs set:cnt = #0 ?>
		set asRecord to {<?cs each:prp=cls.Properties ?><?cs var:prp.Name ?>:get <?cs var:prp.ASaccessor ?> of the<?cs var:cls.Name ?><?cs set: cnt = cnt + #1?><?cs if:cnt != numPrps ?>,<?cs /if ?><?cs /each ?>}
	end tell
	return asRecord
end <?cs var:cls.LCname ?>AsRecord

on write<?cs var:cls.Name?>s(outFD)
  tell application "Palm Desktop"
    writeBeginTag(outFD, "<?cs var:cls.LCname?>s") of me
    <?cs #repeat with the cs var:cls.Name in  cs var:cls.ASaccessor s ?>
    repeat with <?cs var:cls.LCname?>Idx from 1 to <?cs var:idxLimit?>
      set the<?cs var:cls.Name?> to <?cs var:cls.ASaccessor?> <?cs var:cls.LCname?>Idx
      write<?cs var:cls.Name?>(outFD, the<?cs var:cls.Name?>) of me
    end repeat
    writeEndTag(outFD, "<?cs var:cls.LCname?>s") of me
  end tell
end write<?cs var:cls.Name?>s
<?cs /def ?>

<?cs # FIXME: *********** HACK ***************** ?>
<?cs # Hard code stubbed version of writeBeginEntry; see note below about inheritance. ?>
on writeBeginEntry(outFD, tagName, theEntry)
	local tag
	tell application "Palm Desktop"
		set tag to doIndent() of me & "<" & tagName & " xml:id=\"ID" & id of theEntry & "\">"
		writeLine(outFD, tag) of me
		--display dialog "writeBeginEntry " & tag
		--writePrimaryCategory(theEntry) of me
		--writeSecondaryCategory(theEntry) of me
		--writeTagged("private", get private of theEntry) of me
		--writeAttachments(get attachments of theEntry) of me
	end tell
end writeBeginEntry

<?cs # Generate the data-model-dependent code ?>
<?cs # Generate handlers that are data dependent, but for which there is
     # only one generated instance ?>
on getItemCount()
	tell application "Palm Desktop"
          <?cs each: cls=DataModel.Classes ?><?cs if:cls.DependentType ?><?cs else ?>
        set <?cs var:cls.LCname ?>Count to count of <?cs var:cls.ASaccessorPlural?><?cs /if ?>
          <?cs /each ?>
        <?cs set: numClasses = subcount(DataModel.Classes) ?><?cs set:idx = #0 ?>
        return <?cs each:cls=DataModel.Classes ?><?cs if:cls.DependentType ?><?cs else ?><?cs var:cls.LCname ?>Count<?cs /if ?><?cs set: idx = idx + #1?><?cs if:idx != numClasses ?> + <?cs /if ?><?cs /each ?>
	end tell
end getItemCount
<?cs # Generate handlers that are data dependent and there is one instance per 
     # class. ?>
<?cs # TODO: handle inheritance of classes.  In the instance of Palm Desktop, the
     # common super class of Memo, ToDo, Event, and Address, Entry is handled in
     # the original ExportToXML.scpt with the routine "writeBeginEntry" which 
     # forms and writes a opening tag based on the subclass of entry, then writes
     # tag data for the primary and secondary categories, the private property, and
     # attachments. ?>
<?cs each: cls=DataModel.Classes ?><?cs 
  if:cls.DependentType ?><?cs
    call genDepTypeWriter(cls) ?><?cs
  else ?><?cs
    call genIndyTypeWriter(cls) ?><?cs 
  /if ?><?cs /each ?>

<?cs # Main program 
?>on main()
  try
    tell application "Palm Desktop"
      local itemCount
      set itemCount to getItemCount() of me
      startProgressBar("Palm Desktop XML Export", "Exporting to XML", itemCount) of me
      set outFD to openOutFile("palmDesktop.xml") of me
      writeXMLdecl(outFD) of me
      writeBeginTag(outFD, "palmDesktop") of me
      <?cs each cls=DataModel.Classes?><?cs
      if:cls.DependentType?><?cs else ?>
      setFooter("<?cs var:cls.Name?>s") of me
      write<?cs var:cls.Name?>s(outFD) of me<?cs /if ?><?cs /each ?>
      writeEndTag(outFD, "palmDesktop") of me
      closeOutFile(outFD) of me
      if enableProgressBar then tell application "SKProgressBar" to quit
    end tell
  on error errText number errNum partial result partialResult
    closeOutFile(outFD) of me
    if enableProgressBar then tell application "SKProgressBar" to quit
    display dialog "main " & errText & errNum as string
    tell application "Palm Desktop"
      partialResult
    end tell
  end try
  tell application "SKProgressBar" to quit
end main

main() of me