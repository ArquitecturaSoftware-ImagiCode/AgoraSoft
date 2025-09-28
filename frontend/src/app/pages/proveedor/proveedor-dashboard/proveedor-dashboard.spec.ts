import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProveedorDashboard } from './proveedor-dashboard';

describe('ProveedorDashboard', () => {
  let component: ProveedorDashboard;
  let fixture: ComponentFixture<ProveedorDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProveedorDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProveedorDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
