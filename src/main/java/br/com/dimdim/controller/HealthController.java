package br.com.dimdim.controller;
import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/health") public class HealthController { @GetMapping public Map<String,String> health(){return Map.of("status","UP","application","dimdim-music");} }
