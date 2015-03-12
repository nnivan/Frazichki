[1mdiff --git a/app/app-release.apk b/app/app-release.apk[m
[1mdeleted file mode 100644[m
[1mindex b53a202..0000000[m
Binary files a/app/app-release.apk and /dev/null differ
[1mdiff --git a/app/src/main/java/com/ivan/frazichki/AddPhraseFromSentenceTask.java b/app/src/main/java/com/ivan/frazichki/AddPhraseFromSentenceTask.java[m
[1mindex 65ac4ab..9fb73fa 100644[m
[1m--- a/app/src/main/java/com/ivan/frazichki/AddPhraseFromSentenceTask.java[m
[1m+++ b/app/src/main/java/com/ivan/frazichki/AddPhraseFromSentenceTask.java[m
[36m@@ -1,5 +1,6 @@[m
 package com.ivan.frazichki;[m
 [m
[32m+[m[32mimport android.app.Application;[m
 import android.content.Context;[m
 import android.os.AsyncTask;[m
 import android.util.Log;[m
[36m@@ -183,7 +184,7 @@[m [mpublic class AddPhraseFromSentenceTask extends AsyncTask<String, Void, List<Stri[m
         if(!lexicalDAG.equals("")) {[m
             buildPhrase(lexicalDAG);[m
         }else{[m
[31m-            [m
[32m+[m
         }[m
 [m
         return phrases;[m
