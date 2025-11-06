import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProvProductos } from './prov-productos';

describe('ProvProductos', () => {
  let component: ProvProductos;
  let fixture: ComponentFixture<ProvProductos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProvProductos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProvProductos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
