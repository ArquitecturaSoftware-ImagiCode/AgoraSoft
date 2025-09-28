import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComercialLayout } from './comercial-layout';

describe('ComercialLayout', () => {
  let component: ComercialLayout;
  let fixture: ComponentFixture<ComercialLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComercialLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComercialLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
