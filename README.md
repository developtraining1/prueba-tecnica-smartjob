Cómo usar la aplicación.
Descargue el la colección y ábrala usando un postman.
Cree un usuario administrador (que tenga el rol: "ROLE_ADMIN"Genere un token con las credenciales del administrador).
Usando ese token puede crear, listar y buscar usuarios sin restricción.
Los usuarios creados tendrán el rol "ROLE_USER" (solo podrán ver su propia informacion al listar usuarios), (solo podran buscar por id, su propio usuario y no podran ver información ajena), (no tendran permiso para otras accciones, como crear nuevos usarios o ver informacion de usuarios terceros)
