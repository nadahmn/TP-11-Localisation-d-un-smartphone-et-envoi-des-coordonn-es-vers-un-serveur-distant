TP 11 — Localisation d'un smartphone et envoi vers un serveur distant


              PARTIE 1 : Serveur (WAMP/XAMPP)

  -> Étape 1 : Démarrer le serveur local :


Lancez XAMPP :



<img width="496" height="316" alt="Capture d&#39;écran 2026-05-21 221845" src="https://github.com/user-attachments/assets/059cd1fe-8038-406e-abcf-c530e38ae7ea" />



   -> Étape 2 : Créer la base de données MySQL :

   

   <img width="434" height="404" alt="Capture d&#39;écran 2026-05-21 222008" src="https://github.com/user-attachments/assets/e56699c1-4039-44be-83da-619b9ec6fad3" />


    exécutez ce script :
    

    
<img width="791" height="392" alt="Capture d&#39;écran 2026-05-21 221937" src="https://github.com/user-attachments/assets/0efcef29-4885-4f69-896c-f13944cc47e7" />



-> Étape 3 : Créer l'arborescence du projet PHP :


<img width="218" height="230" alt="Capture d&#39;écran 2026-05-21 222452" src="https://github.com/user-attachments/assets/8a5740fe-d95c-4be5-bb0e-79b4488b8cbb" />


   ->  Étape 4 : Créer les fichiers PHP :
   


     classe/Position.php :



<img width="218" height="230" alt="Capture d&#39;écran 2026-05-21 222452" src="https://github.com/user-attachments/assets/2903405b-2668-4a9c-a043-86306edc80de" />



-> Étape 5 : Tester le serveur :

Ouvrez votre navigateur

Allez à : http://localhost/localisation/createPosition.php 



<img width="940" height="491" alt="Capture d&#39;écran 2026-05-21 222552" src="https://github.com/user-attachments/assets/8d2f3ddf-de27-435c-8819-45f7cd94fdff" />




              PARTIE 2 : Android Studio


  -> Étape 6 : Créer un nouveau projet :


  

  <img width="663" height="427" alt="Capture d&#39;écran 2026-05-21 222617" src="https://github.com/user-attachments/assets/0ef6b8bf-9349-4c57-a2e7-b90ce49895f7" />






  -> Étape 7 : Déclarer les permissions



<img width="1105" height="407" alt="image" src="https://github.com/user-attachments/assets/5a446901-d3ff-47aa-8e7d-5fc0dd94dd43" />




   -> Étape 8 : Ajouter la dépendance Volley




<img width="932" height="345" alt="image" src="https://github.com/user-attachments/assets/35d7d5cf-15a5-4b39-956b-10929eef28f0" />




   -> Étape 9 : Créer l'interface utilisateur :



<img width="922" height="332" alt="Capture d&#39;écran 2026-05-21 223714" src="https://github.com/user-attachments/assets/a0aeaa2d-6719-430a-88c9-1fa77bca431d" />




   -> Étape 12 : Récupérer l'IP de votre ordinateur :


   

         ipconfig


         

<img width="839" height="416" alt="Capture d&#39;écran 2026-05-21 224031" src="https://github.com/user-attachments/assets/f7ed6697-ced9-4c9b-a656-773e4995fb83" />



<img width="763" height="425" alt="Capture d&#39;écran 2026-05-21 224020" src="https://github.com/user-attachments/assets/1f689bc9-ba26-4cc4-9ce5-3c38817c7271" />






           Remplace 192.168.1.X dans insertUrl par Notre IP  :



<img width="839" height="416" alt="Capture d&#39;écran 2026-05-21 224031" src="https://github.com/user-attachments/assets/187d0bde-cd8d-40cc-bc55-20b5e0d08266" />





              PARTIE 3 : Tests


   -> Étape 13 : Vérifier la connexion :


   

<img width="179" height="374" alt="Capture d&#39;écran 2026-05-21 223734" src="https://github.com/user-attachments/assets/546a9978-bffd-40a7-b082-8d591498d325" />



<img width="839" height="416" alt="Capture d&#39;écran 2026-05-21 224031" src="https://github.com/user-attachments/assets/5fdbe6cf-c146-4998-ae3d-333646290986" />





  ->  Étape 15 : Vérifier dans phpMyAdmin :

  


<img width="1903" height="400" alt="image" src="https://github.com/user-attachments/assets/393b297f-e6ca-4e67-8bfb-542a5f366139" />

