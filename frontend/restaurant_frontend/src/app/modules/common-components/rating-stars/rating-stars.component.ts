import { Component, EventEmitter, Output } from '@angular/core';
import { faStar } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-rating-stars',
  templateUrl: './rating-stars.component.html',
  styleUrl: './rating-stars.component.css'
})
export class RatingStarsComponent {

  faStar = faStar;
  rating = 1;

  @Output() ratingChanged = new EventEmitter<number>();
  
  setRating(star: number){
    this.rating = star;
    this.ratingChanged.emit(this.rating)
  }
}
