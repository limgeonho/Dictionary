package com.dictionary.dictionary.controller;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.regex.Pattern;

@Controller
@Slf4j
public class MainController {
    @GetMapping("/index")
    public String dictionaryView(){
        return "view/index";
    }

    @PostMapping("/index")
    public String dictionary(@RequestParam("word") String word) throws IOException {

        String[] splittedWord = word.split("");
        if(Pattern.matches("[a-zA-Z]", splittedWord[0])){
            log.info("영어 -> 한국어");

            String loweredWord = word.toLowerCase();

            String url = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=" + loweredWord + "뜻";
            Document document = Jsoup.connect(url).get();
            Elements element = document.getElementsByAttributeValue("class", "mean api_txt_lines");
            log.info(element.get(0).text());

        }else{
            log.info("한국어 -> 영어");

            String url = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=" + word + "영어로";
            Document document = Jsoup.connect(url).get();
            Elements element = document.getElementsByAttributeValue("class", "mean api_txt_lines");
            log.info(element.get(0).text());

        }

      return "redirect:/";
    }
}
