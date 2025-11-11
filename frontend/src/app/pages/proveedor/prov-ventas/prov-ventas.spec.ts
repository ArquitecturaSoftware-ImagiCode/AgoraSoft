import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProvVentas } from './prov-ventas';

describe('ProvVentas', () => {
  let component: ProvVentas;
  let fixture: ComponentFixture<ProvVentas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProvVentas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProvVentas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
