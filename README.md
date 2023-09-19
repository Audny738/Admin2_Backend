# Admin2_Backend

## Run the application

Be located at the root of the ams_backend project

1. Update the [docker-compose.yml](https://github.com/JoshuaMeza/Admin2_Backend/blob/master/ams_backend/docker-compose.yml) file with your mysql root password in the *MYSQL_ROOT_PASSWORD* variable.
2. Run the command ```docker-compose build```
3. Run the command ```docker-compose up```

Optional: Run the ```docker-compose build --no-cache``` command on error.

To verify that everything is working, you can visit the path ```http://localhost:8080/docs``` where you will see a method that returns a string of characters with a hello world!

<p align="center">
  <img src="https://github.com/JoshuaMeza/Admin2_Backend/blob/master/img_readme/hello_world.png">
</p>
