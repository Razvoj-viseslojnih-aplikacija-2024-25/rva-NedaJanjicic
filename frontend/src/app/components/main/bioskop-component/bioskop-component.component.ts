import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { Bioskop } from '../../../models/bioskop';
import { BioskopService } from '../../../services/bioskop-service';
import { BioskopDialog } from '../../dialogs/bioskop-dialog/bioskop-dialog.component';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';

@Component({
  selector: 'app-bioskop-component',
  standalone: true,
  imports: [MatTableModule, MatIconModule, MatToolbarModule, MatPaginatorModule, MatSortModule],
  templateUrl: './bioskop-component.component.html',
  styleUrls: ['./bioskop-component.component.css']
})
export class BioskopComponent implements OnInit {

  displayedColumns = ['id', 'naziv', 'adresa', 'actions'];
  dataSource!: MatTableDataSource<Bioskop>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private bioskopService: BioskopService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.bioskopService.getAllBioskops().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: (error) => console.log(error)
    });
  }

  public openDialog(flag: number, id?: number, naziv?: string, adresa?: string): void {
    const ref = this.dialog.open(BioskopDialog, { data: { id, naziv, adresa } });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe(result => {
      if (result === 1) this.loadData();
    });
  }
}
