import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PenguinGreetingsComponent } from './penguin-greetings.component';

describe('PenguinGreetingsComponent', () => {
  let component: PenguinGreetingsComponent;
  let fixture: ComponentFixture<PenguinGreetingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PenguinGreetingsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PenguinGreetingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
