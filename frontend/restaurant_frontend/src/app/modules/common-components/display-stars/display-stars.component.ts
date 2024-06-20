import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { faStar as faStarSolid } from '@fortawesome/free-solid-svg-icons';
import { faStar as faStarRegular } from '@fortawesome/free-regular-svg-icons';

@Component({
  selector: 'app-display-stars',
  templateUrl: './display-stars.component.html',
  styleUrl: './display-stars.component.css'
})
export class DisplayStarsComponent implements OnChanges {
  @Input() rating: number = 0;
  stars: number[] = [1, 2, 3, 4, 5];
  faStarSolid = faStarSolid;
  faStarRegular = faStarRegular;

  ngOnChanges(changes: SimpleChanges) {
    if (changes['rating']) {
      this.rating = Math.min(5, Math.max(0, changes['rating'].currentValue));
    }
  }
}
