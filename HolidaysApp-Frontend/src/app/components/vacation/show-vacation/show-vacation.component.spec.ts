import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowVacationComponent } from './show-vacation.component';

describe('ShowVacationComponent', () => {
  let component: ShowVacationComponent;
  let fixture: ComponentFixture<ShowVacationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShowVacationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowVacationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
