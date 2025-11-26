import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Autor } from '../models/autor.model';

@Injectable({ providedIn: 'root' })
export class AutorService {
  private api = 'http://localhost:8080/api/autores';

  constructor(private http: HttpClient) {}

  listar(): Observable<Autor[]> {
    return this.http.get<Autor[]>(this.api);
  }

  buscarPorId(id: number): Observable<Autor> {
    return this.http.get<Autor>(`${this.api}/${id}`);
  }

  salvar(entity: any): Observable<any> {
    if (entity.id && entity.id > 0) {
      return this.http.put<any>(`${this.api}/${entity.id}`, entity);
    } else {
      const { id, ...entitySemId } = entity;
      return this.http.post<any>(this.api, entitySemId);
    }
  }

  atualizar(id: number, autor: Autor): Observable<Autor> {
    return this.http.put<Autor>(`${this.api}/${id}`, autor);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}