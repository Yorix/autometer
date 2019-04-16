Set WshShell = CreateObject("WScript.Shell")
Set FSO = CreateObject("Scripting.FileSystemObject")
Set F = FSO.GetFile(Wscript.ScriptFullName)
path = FSO.GetParentFolderName(F)
WshShell.Run  chr(34)&path+"\start.cmd"&chr(34), 0, false
