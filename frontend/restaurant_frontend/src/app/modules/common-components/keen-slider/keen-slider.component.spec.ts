import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KeenSliderComponent } from './keen-slider.component';

describe('KeenSliderComponent', () => {
  let component: KeenSliderComponent;
  let fixture: ComponentFixture<KeenSliderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [KeenSliderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(KeenSliderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
