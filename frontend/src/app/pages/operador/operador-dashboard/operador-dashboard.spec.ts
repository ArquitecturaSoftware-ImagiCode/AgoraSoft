import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperadorDashboard } from './operador-dashboard';

describe('OperadorDashboard', () => {
  let component: OperadorDashboard;
  let fixture: ComponentFixture<OperadorDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OperadorDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OperadorDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
