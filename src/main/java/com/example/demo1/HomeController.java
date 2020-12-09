package com.example.demo1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
    @Autowired
    PalindromicRepo palindromicRepo;



    @GetMapping("/add")
    public String formpage(Model model){
        model.addAttribute("toadd",new toadd());
        return "toAddForm";
    }

    @PostMapping("/process")
    public String process(@RequestParam("word") String word, Model model)
    {
        String subString = longestPalindrome(word);
        toadd add=new toadd(word, subString);
        palindromicRepo.save(add);
        model.addAttribute("toadd",add);

        return "showResult";
    }

    @GetMapping("/list")
    public String listpage(Model model){
        model.addAttribute("toadds",palindromicRepo.findAll());
        return "list";

    }

// Methods to calculate the longest palindrome
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }
    public static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }
}