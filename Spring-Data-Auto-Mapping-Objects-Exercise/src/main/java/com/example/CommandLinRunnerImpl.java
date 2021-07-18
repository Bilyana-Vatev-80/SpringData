package com.example;

import com.example.model.dto.*;
import com.example.service.GameService;
import com.example.service.UserService;
import com.example.util.ValidationUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CommandLinRunnerImpl implements CommandLineRunner {
        private final BufferedReader bufferedReader;
        private final UserService userService;
        private final GameService gameService;
        private final ValidationUtil validationUtil;

        public CommandLinRunnerImpl(UserService userService, GameService gameService, ValidationUtil validationUtil) {
            this.userService = userService;
            this.gameService = gameService;

            this.validationUtil = validationUtil;
            this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

    @Override
    public void run(String... args) throws Exception {
        while (true){
            System.out.println("Please enter your command:");
            String[] commands = bufferedReader.readLine().split("\\|");

            switch (commands[0]){
                case "RegisterUser":

                    if(!commands[2].equals(commands[3])){
                        System.out.println("Enter correct password");
                        break;
                    }
                    UserRegisterDto userRegisterDto = new UserRegisterDto(commands[1],commands[2],commands[4]);

                    if(this.validationUtil.isValid(userRegisterDto)){
                        this.userService.registerUser(userRegisterDto);

                        System.out.printf("%s was registered%n",commands[4]);
                    } else {

                        this.validationUtil.violation(userRegisterDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }


                    break;
                case "LoginUser":
                   UserLoginDto userLoginDto = new UserLoginDto(commands[1],commands[2]);
                   this.userService.loginUser(userLoginDto);
                    break;

                case "Logout":
                    this.userService.logOut();
                    break;

                case "AddGame":
                   try {
                     GameAddDto gameAddDto = new GameAddDto(commands[1],new BigDecimal(commands[2]),Double.parseDouble(commands[4]),
                             commands[5],commands[6],LocalDate.parse(commands[7], DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                     if(this.validationUtil.isValid(gameAddDto)){
                         this.gameService.addGame(gameAddDto);
                         System.out.println("Added " + gameAddDto.getTitle());
                     } else {
                         this.validationUtil.violation(gameAddDto)
                                 .stream()
                                 .map(ConstraintViolation::getMessage)
                                 .forEach(System.out::println);
                     }
                   }catch (NullPointerException ex){
                       System.out.println("No logged in user! Please, log in to make changes!");
                   }
                   break;
                case "EditGame":
                    try {
                        GameEditDto gameEditDto = this.gameService.findOneById(Long.parseLong(commands[1]));

                        if (gameEditDto == null) {
                            System.out.printf("Game with Id %s doesn't exist!%n", commands[1]);
                            return;
                        }

                        gameEditDto.setId(Long.parseLong(commands[1]));

                        for (int i = 2; i < commands.length; i++) {
                            String[] tokens = commands[i].split("=");
                            switch (tokens[0]) {
                                case "title":
                                    gameEditDto.setTitle(tokens[1]);
                                    break;
                                case "price":
                                    gameEditDto.setPrice(new BigDecimal(tokens[1]));
                                    break;
                                case "size":
                                    gameEditDto.setSize(Double.parseDouble(tokens[1]));
                                    break;
                                case "thumbnail":
                                    gameEditDto.setImage(tokens[1]);
                                    break;
                                case "trailer":
                                    gameEditDto.setTrailer(tokens[1]);
                                    break;
                                case "description":
                                    gameEditDto.setDescription(tokens[1]);
                                    break;
                                case "date":
                                    gameEditDto.setReleaseDate(LocalDate.parse(tokens[1],
                                            DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                                    break;
                            }

                            if(this.validationUtil.isValid(gameEditDto)) {
                                this.gameService.editGame(gameEditDto);
                            } else {
                                this.validationUtil.violation(gameEditDto)
                                        .stream()
                                        .map(ConstraintViolation::getMessage)
                                        .forEach(System.out::println);
                            }
                        }
                    } catch (NullPointerException ex) {
                        System.out.println("No logged in user! Please, log in to make changes!");
                    }
                    break;

                case "DeleteGame":
                    GameDeleteDto gameDeleteDto = this.gameService.findById(Long.parseLong(commands[1]));
                    this.gameService.deleteGame(gameDeleteDto);
                    break;
                case "AllGames":
                    this.gameService
                            .getAllGameTitlesAndPrices()
                            .forEach(game -> System.out.println(game.getTitle() + " " + game.getPrice()));
                    break;
                case "DetailGame":
                    GameSingleTitleDetailsViewDto gameSingleTitleDetails =
                            this.gameService.getSingleTitleDetails(commands[1]);

                    String date = gameSingleTitleDetails.getReleaseDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    System.out.println(
                            String.format(
                                    "Title: %s\r\n" +
                                            "Price: %.2f\r\n" +
                                            "Description: %s\r\n" +
                                            "Release date: %s\r\n",
                                    gameSingleTitleDetails.getTitle(),
                                    gameSingleTitleDetails.getPrice(),
                                    gameSingleTitleDetails.getDescription(),
                                    date
                            )
                    );
                    break;

            }
        }

    }
}

