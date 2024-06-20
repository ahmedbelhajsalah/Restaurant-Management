import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayStarsComponent } from './display-stars.component';

describe('DisplayStarsComponent', () => {
  let component: DisplayStarsComponent;
  let fixture: ComponentFixture<DisplayStarsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DisplayStarsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DisplayStarsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
