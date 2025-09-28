import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperadorLayout } from './operador-layout';

describe('OperadorLayout', () => {
  let component: OperadorLayout;
  let fixture: ComponentFixture<OperadorLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OperadorLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OperadorLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
