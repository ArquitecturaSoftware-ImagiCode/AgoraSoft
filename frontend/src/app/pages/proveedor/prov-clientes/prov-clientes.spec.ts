import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProvClientes } from './prov-clientes';

describe('ProvClientes', () => {
  let component: ProvClientes;
  let fixture: ComponentFixture<ProvClientes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProvClientes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProvClientes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
