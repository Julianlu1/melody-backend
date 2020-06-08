package server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import server.GeneralException;
import server.config.JwtTokenUtil;
import server.entity.User;
import server.logic.JwtService;
import server.logic.UserLogic;
import server.model.JwtRequest;
import server.model.JwtResponse;
import server.model.RegisterRequest;
import server.service.JwtUserDetailsService;

import java.util.Map;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

        @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){

        User user = new User(registerRequest.getUsername(),registerRequest.getPassword());
        try {
            userDetailsService.registerUser(user);
            return new ResponseEntity("Succesvol",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity("Helaas", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    public ResponseEntity getUser(@RequestHeader (name="Authorization") String token){
       try{
           jwtService.decodeJwt(token);
           int userId = jwtService.getIdFromToken();
           User user = userLogic.findById(userId);
           return ResponseEntity.ok(user);
       }catch(Exception e){
           GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
           String jsonString = gson.toJson(ex);
           return new ResponseEntity<>(jsonString,HttpStatus.BAD_REQUEST);
       }

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            e.printStackTrace();
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
