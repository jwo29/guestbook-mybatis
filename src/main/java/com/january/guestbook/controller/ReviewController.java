package com.january.guestbook.controller;

import com.january.guestbook.dto.ReviewDTO;
import com.january.guestbook.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getAllReviews(@PathVariable("mno") Long mno){
        return ResponseEntity.ok(reviewService.getListOfMovie(mno));
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReivew(@PathVariable("mno") Long mno, @RequestBody ReviewDTO reviewDTO) {
        Long reviewNum = reviewService.register(reviewDTO);
        return ResponseEntity.ok(reviewNum);
    }

    @PostMapping("/{mno}/{reviewNum}")
    public ResponseEntity<Void> modifyReview(@PathVariable Long mno,
                                             @PathVariable Long reviewNum,
                                             @RequestBody ReviewDTO reviewDTO) {
        reviewService.modify(reviewDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{mno}/{reviewNum}")
    public ResponseEntity<Void> removeReview(@PathVariable("mno") Long mno, @PathVariable("reviewNum") Long reviewNum) {
        reviewService.delete(reviewNum);
        return ResponseEntity.ok().build();
    }
}
