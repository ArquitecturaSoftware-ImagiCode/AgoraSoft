import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProveedorLayout } from './proveedor-layout';

describe('ProveedorLayout', () => {
  let component: ProveedorLayout;
  let fixture: ComponentFixture<ProveedorLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProveedorLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProveedorLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
